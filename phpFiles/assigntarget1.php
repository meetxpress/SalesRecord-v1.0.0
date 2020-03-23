<?php
	$res=array();
	if(isset ($_POST['comp_id']) && ($_POST['atargetmonth']) && ($_POST['atarget'])){
		
		//($$_POST['comp_id'])
		//$$_POST['comp_id'];
		$comp_id=$_POST['comp_id'];
		$atargetmonth=$_POST['atargetmonth'];
		$atarget=$_POST['atarget'];
		
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		$qry1=mysqli_query($con,"select atargetmonth from emp_target where atargetmonth='$atargetmonth' and comp_id='$comp_id'");
		
		if(mysqli_num_rows($qry1) > 0 ){
			$qry2=mysqli_query($con,"UPDATE emp_target SET atarget = '$atarget' where comp_id='$comp_id' and atargetmonth='$atargetmonth'");
			
			if($qry2){
				$res["success"]=1;
				$res["message"]="Target Updated Successfully.";
			
				echo json_encode($res);
			}else{
				$res["success"]=0;					
				$res["message"]="Can't updated Target.";
				
				echo json_encode($res);
			}	
		}else{
			$qry3=mysqli_query($con,"insert into emp_target(comp_id,atargetmonth,atarget) values ('$comp_id','$atargetmonth','$atarget')");
			if($qry3){
				$res["success"]=1;
				$res["message"]="Target Inserted Successfully.";
				
				echo json_encode($res);
			}else{
				$res["success"]=0;					
				$res["message"]="Can't inserted Target.";
					
				echo json_encode($res);
			}
		}
		
		//$at=($atarget)/(mysqli_num_rows($qry));
		
		//$qry1=mysqli_query($con,"UPDATE employee_master SET etarget = '$at',emonth=('$assignmonth')");
		//$qry2=mysqli_query($con,"insert into target_master(target,t_month,comp_id) values ('$at','$assignmonth','$comp_id')");
	
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
