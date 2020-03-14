<?php
	$res = array();
	if(isset($_POST['emp_id'])){

		$con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		$qry = mysqli_query($con,"select * from employee_master where emp_id = '".$_POST['emp_id']."'");
		//$data  =  $con->query($qry);   //$con->query($sql);
		if(mysqli_num_rows($qry) > 0 ){
			$row = mysqli_fetch_assoc($qry);
			
			$res["success"] = 1;
			$res["message"] = "Data found Successfully.";
			
			$res["emp_name"] = $row["emp_name"];
			$res["emp_id"] = $row["comp_id"];
			$res["emp_mob1"] = $row["emp_mob1"];
			$res["emp_address"] = $row["emp_address"];			
			
			echo json_encode($res);
		}else{
			$res["success"] = 0;					
			$res["message"] = "No data found";
					 
			echo json_encode($res);
		}		
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Fields are missing.";
		
		echo json_encode($res);
	}
?>