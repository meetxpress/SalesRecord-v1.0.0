<?php
	$res=array();
    if(isset($_POST['emp_id']) && ($_POST['fDate']) &&  ($_POST['tDate']) && ($_POST['type']) && ($_POST['type2']) && ($_POST['reason']) && ($_POST['status']) ){ 
		// 
        //?leave_id=1001&emp_id=300001&fDate=2020-12-12&tDate=2020-12-14&type=Full Day&type2=Full Day&reason=Education&status=pending
	
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
		$qry=mysqli_query($con,"insert into leave_management(emp_id, fromDate, toDate, type1, type2, reason, status) values('".$_POST['emp_id']."','".$_POST['fDate']."', '".($_POST['tDate'])."', '".$_POST['type']."', '".$_POST['type2']."', '".$_POST['reason']."', '".$_POST['status']."')");

		if($qry){
			$res["success"] = 1;
			$res["message"] = "Leave Applied Successfully.";
			
			echo json_encode($res);
		}else{
			$res["success"] = 0;					
			$res["message"] = "Can't apply Leave.";
				
			echo json_encode($res);
		}		
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>
