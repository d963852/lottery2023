package com.jeesite.modules.api.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jeesite.common.service.BaseService;
import com.jeesite.modules.lotterycore.entity.Game;
import com.jeesite.modules.lotterycore.entity.Issue;
import com.jeesite.modules.lotterycore.service.GameService;
import com.jeesite.modules.lotterycore.service.IssueService;
import com.jeesite.modules.lotterycore.vo.IssueVO;
import com.jeesite.modules.sys.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws/gameBettingWebSocketService/{sid}/{gameCode}")
@Component
@Service
public class GameBettingWebSocketService extends BaseService {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<GameBettingWebSocketService> webSocketSet = new CopyOnWriteArraySet<GameBettingWebSocketService>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid = "";
    //接收gameCode
    private String gameCode = "";

    private static GameService gameService;
    private static IssueService issueService;
    private static MemberService memberService;

    @Autowired
    public void setGameService(GameService gameService) {
        GameBettingWebSocketService.gameService = gameService;
    }

    @Autowired
    public void setIssueService(IssueService issueService) {
        GameBettingWebSocketService.issueService = issueService;
    }

    @Autowired
    public void setMemberService(MemberService memberService) {
        GameBettingWebSocketService.memberService = memberService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid, @PathParam("gameCode") String gameCode) {
        this.session = session;
//        TelegramUtils.sendMessage("新链接："+sid);
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        this.sid = sid;
        this.gameCode = gameCode;
        try {
            logger.info(this.sid + "连接GameBettingWebSocketService");
        } catch (Exception e) {
            logger.error("websocket IO异常");
        }


    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
//        TelegramUtils.sendMessage("断掉链接："+sid);
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自请求" + sid + "的信息:" + message);
        try {
            if (StrUtil.isEmpty(message)) {
                logger.info("消息体为空，不予处理");
                return;
            }
            Map<String, String> result = new HashMap<>();
            if ("updateCurrentIssue".equals(message)) {
                result.put("responseOf", "updateCurrentIssue");
                // 处理期号更新请求
                Game game = gameService.getGameByCode(gameCode);
                if (game != null) {
                    result.put("currentIssueNumber", game.getCurrentIssueNumber());
                    result.put("currentIssueEndTime", DateUtil.format(game.getCurrentIssueEndTime(), "yyyy-MM-dd HH:mm:ss"));
                    result.put("lastIssueNumber", game.getLastIssueNumber());
                    result.put("lastIssueLotteryNumber", game.getLastIssueLotteryNumber());
                }
                sendMessage(JSONUtil.toJsonStr(result));
            } else if (message.startsWith("getLastIssueLotteryNumber")) {
                result.put("responseOf", "getLastIssueLotteryNumber");
                String[] lastIssueLotteryNumberMsg = message.split(":");
                if (lastIssueLotteryNumberMsg.length == 2) {
                    String issueNumber = lastIssueLotteryNumberMsg[1];
                    // 处理开奖号码更新请求
                    Issue issue = null;
                    while (issue == null || StrUtil.isEmpty(issue.getLotteryNum())) {
                        issue = issueService.getByGameCodeAndIssueNumber(gameCode, issueNumber);
                        if (StrUtil.isEmpty(issue.getLotteryNum())) {
                            Thread.currentThread().sleep(1000);
                        }
                    }

                    result.put("lastIssueLotteryNumber", issue.getLotteryNum());
                    // 获取过去10次历史数据
                    List<Issue> historyList = issueService.findHistory(gameCode, 10);
//                    Map<String, String> historyRefreshData = new HashMap<>();
//                    for (int i = historyList.size(); i > 0; i--) {
//                        historyRefreshData.put(historyList.get(i-1).getIssueNum().toString(), historyList.get(i-1).getLotteryNum().toString());
//                    }
                    List<IssueVO> historyRefreshData = new ArrayList<>();
                    for(Issue theIssue : historyList){
                        IssueVO issueVO = new IssueVO();
                        issueVO.setIssueNum(theIssue.getIssueNum());
                        issueVO.setLotteryNum(theIssue.getLotteryNum());
                        historyRefreshData.add(issueVO);
                    }
                    result.put("historyRefreshData", JSONUtil.toJsonStr(historyRefreshData));
                    sendMessage(JSONUtil.toJsonStr(result));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    //实现服务器主动推送
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    //群发自定义消息
    public static void sendInfo(String message, @PathParam("sid") String sid)
            throws IOException {
        for (GameBettingWebSocketService item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        GameBettingWebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        GameBettingWebSocketService.onlineCount--;
    }
}