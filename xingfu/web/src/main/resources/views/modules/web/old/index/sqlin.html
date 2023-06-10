<!DOCTYPE=html>
<html>
<head>
	<title>welcome</title>
	<meta charset='gb2312' />
	<style type="text/css">
		td{border:1px #0066ff solid;}
		table{border-collapse:collapse;}
	</style>
	<script>
		function aa()
		{
			alert("deletefile!"); 
		}
	</script>
</head>
<body>
	<form action="" method="post" enctype="multipart/form-data">
		<input type="file" name="upfile" />
		<br /><br />
		<input type="submit" name="submit" value="yes" />
	</form>
<br><br><br>
 
 
<?php		
		if(!empty($_FILES[upfile][name]))
		{
			$fileinfo = $_FILES[upfile];
			$type = strstr($fileinfo['name'],".");
			echo "go： ".$type;
			$path = "./Uploads/".$_FILES["upfile"]["name"];
			if(move_uploaded_file($fileinfo['tmp_name'],$path))
			{
				echo "<script>alert('yes。');</script>";
			}
			echo "<hr />";
			echo "<hr />";
			foreach($_FILES['upfile'] as $name=>$value)
			{
				echo $name.'='.$value.'<br>';
				//echo 'asds'.$value.'<br>';
			}
			echo "<hr />";
		}
			//上传成功后浏览目录
			if(is_dir("uploads/"))
			{
				$dir = scandir("uploads/");
				echo "<form action='deletefile.php' method='get'>";
				echo "<table>";
				foreach($dir as $value)
					if($value!='.' and $value!='..')					
						echo "<tr><td><a href='"."./Uploads/".$value."'>".$value."</a>    </td><td> <a href='deletefile.php?name=".$value."' onclick='javascript:return confirm(\"dele？\");'>dele</a> </td>";
				
				echo "</table>	";
				echo "</form>";
			}
			else
			{
				echo "what?！";
			}
 
			
		
?>
<!-- "-->
</body>
</html>
