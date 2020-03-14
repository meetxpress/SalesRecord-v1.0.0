<?php
	$res=array();
	if(isset($_POST['username']) && ($_POST['password'])){
		$user=$_POST['username'];//"admin";
		$pass=$_POST['password'];//"adm123";
		
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
			
		if(substr($_POST['username'], 0, 1) == 'a'){
			$qry = mysqli_query($con,"select * from super_admin where username='$user' and password='$pass'");
			if(mysqli_num_rows($qry) > 0){			
				$res["success"] = 1;
				$res["message"]="Logged in Successfully.";
		
				echo json_encode($res);
			}else{
				$res["success"]=0;					
				$res["message"]="Invalid Credentials!";
			
				echo json_encode($res);
			}			

		}else if(substr($_POST['username'], 0, 1) == '1'){
			$qry = mysqli_query($con,"select * from company_master where comp_id='$user' and comp_password='$pass' and comp_status='Active'");
			if(mysqli_num_rows($qry) > 0){			
				//echo "<h1> Company </h1>";
				$res["success"]=2;
				$res["message"]="Logged in Successfully.";
		
				echo json_encode($res);
			}else{
				$res["success"]=0;					
				$res["message"]="Invalid Credentials!";
			
				echo json_encode($res);
			}				
		}else if(substr($_POST['username'], 0, 1) == '3'){
			$qry = mysqli_query($con,"select * from employee_master where emp_id='$user' and emp_password='$pass' and emp_status='Active'");
			if(mysqli_num_rows($qry) > 0){			
				//echo "<h1> Employee </h1>";
				$res["success"]=3;
				$res["message"]="Logged in Successfully.";
		
				echo json_encode($res);
			}else{
				$res["success"]=0;					
				$res["message"]="Invalid Credentials!";
			
				echo json_encode($res);
			}			
		}else{
			//echo "<h1>Invalid user</h1>";
			$res["success"]=0;					
			$res["message"]="Invalid Credentials!";
			
			echo json_encode($res);						
		}

	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>