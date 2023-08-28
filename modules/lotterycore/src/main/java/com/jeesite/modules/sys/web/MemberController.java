package com.jeesite.modules.sys.web;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.lang.TimeUtils;
import com.jeesite.common.shiro.realm.LoginInfo;
import com.jeesite.common.shiro.session.SessionDAO;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.Member;
import com.jeesite.modules.sys.service.MemberService;
import com.jeesite.modules.sys.utils.SysCacheUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 乐透会员管理Controller
 * @author tg
 * @version 2023-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/member")
public class MemberController extends BaseController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private SessionDAO sessionDAO;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Member get(String memId, boolean isNewRecord) {
		return memberService.get(memId, isNewRecord);
	}

	/**
	 * 管理主页
	 */
	@RequiresPermissions("sys:member:view")
	@RequestMapping(value = "index")
	public String index(Member member, Model model) {
		model.addAttribute("member", member);
		return "modules/sys/memberIndex";
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sys:member:view")
	@RequestMapping(value = {"list", ""})
	public String list(Member member, Model model) {
		model.addAttribute("member", member);
		return "modules/sys/memberList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sys:member:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<Member> listData(Member member) {
		if (StringUtils.isBlank(member.getParentCode())) {
			member.setParentCode(Member.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(member.getMemName())){
			member.setParentCode(null);
		}
		if (member.getBalance() != null){
			member.setParentCode(null);
		}
		if (member.getLatelyLoginTime() != null){
			member.setParentCode(null);
		}
		if (member.getRegisteredTime() != null){
			member.setParentCode(null);
		}
		if (member.getAccountLevel() != null){
			member.setParentCode(null);
		}
		if (member.getRebate() != null){
			member.setParentCode(null);
		}
		if (member.getOdds() != null){
			member.setParentCode(null);
		}
		if (member.getWage() != null){
			member.setParentCode(null);
		}
		if (member.getBonus() != null){
			member.setParentCode(null);
		}
		if (StringUtils.isNotBlank(member.getRemarks())){
			member.setParentCode(null);
		}
		List<Member> list = memberService.findList(member);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sys:member:view")
	@RequestMapping(value = "form")
	public String form(Member member, Model model) {
		// 创建并初始化下一个节点信息
		member = createNextNode(member);
		model.addAttribute("member", member);
		return "modules/sys/memberForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public Member createNextNode(Member member) {
		if (StringUtils.isNotBlank(member.getParentCode())){
			member.setParent(memberService.get(member.getParentCode()));
		}
		if (member.getIsNewRecord()) {
			Member where = new Member();
			where.setParentCode(member.getParentCode());
			Member last = memberService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				member.setTreeSort(last.getTreeSort() + 30);
				member.setMemId(IdGen.nextCode(last.getMemId()));
			}else if (member.getParent() != null){
				member.setMemId(member.getParent().getMemId() + "001");
			}
		}
		// 以下设置表单默认数据
		if (member.getTreeSort() == null){
			member.setTreeSort(Member.DEFAULT_TREE_SORT);
		}
		return member;
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("sys:member:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Member member) {
		memberService.save(member);
		return renderResult(Global.TRUE, text("保存会员成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Member member) {
		Member where = new Member();
		where.setStatus(Member.STATUS_NORMAL);
		where.setParentCodes("," + member.getId() + ",");
		long count = memberService.findCount(where);
		if (count > 0) {
			return renderResult(Global.FALSE, text("该会员包含未停用的子会员！"));
		}
		member.setStatus(Member.STATUS_DISABLE);
		memberService.updateStatus(member);
		return renderResult(Global.TRUE, text("停用会员成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Member member) {
		member.setStatus(Member.STATUS_NORMAL);
		memberService.updateStatus(member);
		return renderResult(Global.TRUE, text("启用会员成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Member member) {
		memberService.delete(member);
		return renderResult(Global.TRUE, text("删除会员成功！"));
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param parentCode 设置父级编码返回一级
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("sys:member:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String parentCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		Member where = new Member();
		where.setStatus(Member.STATUS_NORMAL);
		if (StringUtils.isNotBlank(parentCode)){
			where.setParentCode(parentCode);
		}
		List<Member> list = memberService.findList(where);
		for (int i=0; i<list.size(); i++){
			Member e = list.get(i);
			// 过滤非正常的数据
			if (!Member.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getMemId(), e.getMemName()));
			map.put("isParent", !e.getIsTreeLeaf());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(Member member){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		memberService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}


	/**
	 * 在线用户列表
	 * @param model
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "onlineList")
	public String onlineList(Model model) {
		return "modules/sys/onlineMemberList";
	}

	/**
	 * 在线用户列表数据
	 * @author ThinkGem
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "onlineListData")
	@ResponseBody
	public List<Map<String, Object>> onlineListData(String isAllOnline, String isVisitor, String sessionId,
											  String userCode, String userName, String userType, String orderBy) {
		List<Map<String, Object>> list = ListUtils.newArrayList();
		boolean excludeLeave = isAllOnline==null || !Global.YES.equals(isAllOnline);
		boolean excludeVisitor = isVisitor==null || !Global.YES.equals(isVisitor);
		Collection<Session> sessions = sessionDAO.getActiveSessions(excludeLeave,
				excludeVisitor, null, sessionId, userCode);
		long currentTime = System.currentTimeMillis();
		for (Session session : sessions){
			if (StringUtils.isNotBlank(userName) && ((String)session.getAttribute("userName")).contains(userName)){
				continue;
			}
			if (StringUtils.isNotBlank(userType) && ((String)session.getAttribute("userType")).equals(userType)){
				continue;
			}
			Map<String, Object> map = MapUtils.newLinkedHashMap();
			// 为了安全性，需要有权限的人才能看
			if (UserUtils.getSubject().isPermitted("sys:member:edit")){
				map.put("id", session.getId().toString());
			}
			map.put("startTimestamp", DateUtils.formatDateTime(session.getStartTimestamp()));
			map.put("lastAccessTime", DateUtils.formatDateTime(session.getLastAccessTime()));
			map.put("timeout", TimeUtils.formatTime(session.getTimeout()-(currentTime-session.getLastAccessTime().getTime())));
			map.put("userCode", session.getAttribute("userCode"));
			map.put("userName", session.getAttribute("userName"));
			map.put("userType", session.getAttribute("userType"));
			map.put("deviceType", session.getAttribute("deviceType"));
			map.put("device", session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY.primaryPrincipal.params.device"));
			map.put("host", session.getHost());
			list.add(map);
		}
		// 本地排序
		if (StringUtils.isBlank(orderBy)){
			orderBy = "lastAccessTime desc";
		}
		final String[] ss = orderBy.trim().split(" ");
		if (ss != null && ss.length == 2){
			Collections.sort(list, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					String s1 = (String)o1.get(ss[0]);
					String s2 = (String)o2.get(ss[0]);
					if (s1 == null || s2 == null){
						return -1;
					}
					if ("asc".equals(ss[1])){
						return s1.compareTo(s2);
					}else{
						return s2.compareTo(s1);
					}
				}});
		}
		return list;
	}

	/**
	 * 在线用户强制下线
	 * @author ThinkGem
	 */
	@RequiresPermissions("sys:member:edit")
	@RequestMapping(value = "tickOut")
	@ResponseBody
	public String kickOut(String sessionId) {
		Session session = sessionDAO.readSession(sessionId);
		if (session != null){
			Map<String, String> onlineTickOutMap = SysCacheUtils.get("onlineTickOutMap");
			if (onlineTickOutMap == null){
				onlineTickOutMap = MapUtils.newConcurrentMap();
			}
			Object pc = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if (pc != null && pc instanceof PrincipalCollection){
				Object pp = ((PrincipalCollection)pc).getPrimaryPrincipal();
				if (pp != null) {
					if (pp instanceof LoginInfo){
						LoginInfo loginInfo = ((LoginInfo)pp);
						String key = loginInfo.getId()+"_"+loginInfo.getParam("deviceType", "pc");
						onlineTickOutMap.put(key, StringUtils.EMPTY);
					}
				}
			}
			SysCacheUtils.put("onlineTickOutMap", onlineTickOutMap);
			sessionDAO.delete(session);
			return renderResult(Global.TRUE, text("踢出已成功！"));
		}
		return renderResult(Global.FALSE, text("踢出失败，没有找到该在线用户！"));
	}
	
}