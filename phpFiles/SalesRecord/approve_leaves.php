<?php
	$res=array();
	if(isset($_POST['emp_id']) && ($_POST['leave_id']) && ($_POST['status'])){

        //?leave_id=1&status=Approved
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
		$qry=mysqli_query($con,"UPDATE leave_management SET status = '".$_POST['status']."' where leave_id = '".$_POST['leave_id']."'");
		
		if($qry){
			$res["success"] = 1;
			$res["message"] = "Leave Approved.";
			
			echo json_encode($res);
		}else{
			$res["success"] = 0;					
			$res["message"] = "Something went wrong.";
					 
			echo json_encode($res);
		}		
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>