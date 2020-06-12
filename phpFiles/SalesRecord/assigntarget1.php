<?php
	$res=array();
	if(isset ($_POST['comp_id']) && ($_POST['atargetmonth']) && ($_POST['atarget'])){
		
		
		$comp_id=$_POST['comp_id'];
		$atargetmonth=$_POST['atargetmonth'];
		$atarget=$_POST['atarget'];
		$c=0;
		$atarget1=(int)$atarget;
		
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		
		$s1=mysqli_query($con,"select emp_month from emp_sal where emp_month='$atargetmonth'");
		if(mysqli_num_rows($s1) > 0 )
		{
			$ls1=mysqli_query($con,"select emp_id from employee_master");
			if(mysqli_num_rows($ls1) > 0 )
			{
				while($loopsal= mysqli_fetch_assoc($ls1)){
					$array[] = $loopsal;
					$c++;
				}
								
				for($i=0; $i<$c; $i++)
				{
					//$qs2=mysqli_query($con,"insert into emp_sal (emp_id,emp_bsal,emp_inc,emp_totsal,emp_month) values ('".$array[$i]['emp_id']."','10000','0','10000','$atargetmonth')");
					$qs2=mysqli_query($con,"update emp_sal set emp_bsal='10000', emp_inc='0', emp_totsal='10000' where emp_id='".$array[$i]['emp_id']."' and emp_month= '$atargetmonth'");
				}
				$c=0;
			}	
			else
			{
				//emp_master emp id null
			}
		}
		else
		{
			$ls1=mysqli_query($con,"select emp_id from employee_master");
			if(mysqli_num_rows($ls1) > 0 )
			{
				while($loopsal= mysqli_fetch_assoc($ls1)){
					$array[] = $loopsal;
					$c++;
				}
								
				for($i=0; $i<$c; $i++)
				{
					$qs2=mysqli_query($con,"insert into emp_sal (emp_id,emp_bsal,emp_inc,emp_totsal,emp_month) values ('".$array[$i]['emp_id']."','10000','0','10000','$atargetmonth')");
					
				}
				$c=0;
			}	
			else
			{
				//emp_master emp id null
			}
				
		}
		
		
		$qry=mysqli_query($con,"SELECT COUNT(*) as count FROM employee_master where comp_id='$comp_id'");
		
		 if(mysqli_num_rows($qry) > 0 )
		 {
			$row = mysqli_fetch_assoc($qry);
		 }
		
		
		$at=$atarget1/$row["count"];
		
		
		$q1=mysqli_query($con,"select p_month from emp_ptarget where p_month='$atargetmonth'");
		if(mysqli_num_rows($q1) > 0 )
		{
			//target found;
			
			$l1=mysqli_query($con,"select emp_id from employee_master");
			if(mysqli_num_rows($l1) > 0 )
			{
				while($loop= mysqli_fetch_assoc($l1)){
					$array[] = $loop;
					$c++;
				}
								
				for($i=0; $i<$c; $i++)
				{
					
					$q2=mysqli_query($con,"update emp_ptarget set p_target='$at' where emp_id='".$array[$i]['emp_id']."' and p_month='$atargetmonth'"); 		
					
					//echo "update";
					
				}
				$c=0;
			}	
			else
			{
				//emp_master emp id null
			}
		}	
		
		else
		{
			
			$l1=mysqli_query($con,"select emp_id from employee_master");
			if(mysqli_num_rows($l1) > 0 )
			{
				while($loop= mysqli_fetch_assoc($l1)){
					$array[] = $loop;
					$c++;
				}
								
				for($i=0; $i<$c; $i++)
				{
					$q2=mysqli_query($con,"insert into emp_ptarget(comp_id,p_target,p_month,emp_id) values ('$comp_id','$at','$atargetmonth','".$array[$i]['emp_id']."')");
					//echo "insert";
					
				}
				$c=0;
			}	
			else
			{
				//emp_master emp id null
			}
			
		
			if($q2){
			$res["success"]=1;
			$res["message"]="Target Inserted Successfully.";
			
			echo json_encode($res);
			}else{
				$res["success"]=0;					
				$res["message"]="Can't inserted Target.";
				
				echo json_encode($res);
			}
		}
		
		
		
		$qry1=mysqli_query($con,"select atargetmonth from emp_target where atargetmonth='$atargetmonth'");
		
		
		if(mysqli_num_rows($qry1) > 0 )
		{
			
			$qry2=mysqli_query($con,"UPDATE emp_target SET atarget = '$atarget',p_target='$at' where comp_id='$comp_id' and atargetmonth='$atargetmonth'");
			
			if($qry2){
			$res["success"]=1;
			$res["message"]="Target Updated Successfully.";
			
			echo json_encode($res);
		}else{
			$res["success"]=0;					
			$res["message"]="Can't updated Target.";
				
			echo json_encode($res);
		}	
		}
		
		else
		{
			$qry3=mysqli_query($con,"insert into emp_target(comp_id,atargetmonth,atarget,p_target) values ('$comp_id','$atargetmonth','$atarget','$at')");
		
		
			if($qry3)
			{
				$res["success"]=1;
				$res["message"]="Target Inserted Successfully.";
				
				echo json_encode($res);
			}else
			{
				$res["success"]=0;					
				$res["message"]="Can't inserted Target.";
					
				echo json_encode($res);
			}
		}
		
		
		

				
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
