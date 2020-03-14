<?php
	$res = array();
	if(isset($_POST['emp_id'])){

		$con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		$qry = mysqli_query($con,"select * from employee_master where emp_id = '".$_POST['emp_id']."'");
        //$data  =  $con->query($qry);   //$con->query($sql);
        
		if( mysqli_num_rows($qry) > 0 ){
			$row = mysqli_fetch_assoc($qry);
			
			$res["success"] = 1;
			$res["message"] = "Data found Successfully.";
			
            $res["emp_name"] = $row["emp_name"];
            $res["emp_gen"] = $row["emp_gen"];
            $res["emp_dob"] = $row["emp_dob"];
            $res["emp_aadharno"] = $row["emp_aadharno"];
            $res["emp_mob1"] = $row["emp_mob1"];
            $res["emp_email"] = $row["emp_email"];
            $res["emp_address"] = $row["emp_address"];
            $res["emp_pincode"] = $row["emp_pincode"];		
            $res["emp_city"] = $row["emp_city"];			
			$res["emp_state"] = $row["emp_state"];			
			
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