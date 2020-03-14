<?php
	$res = array();
	if(isset($_POST['com_id'])){

		$con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		$qry = mysqli_query($con,"select * from company_master where comp_id = '".$_POST['com_id']."'");
		//$data  =  $con->query($qry);   //$con->query($sql);
		if(mysqli_num_rows($qry) > 0 ){
			$row = mysqli_fetch_assoc($qry);
			
			$res["success"] = 1;
			$res["message"] = "Data found Successfully.";
			
			$res["comp_name"] = $row["comp_name"];
			$res["comp_city"] = $row["comp_city"];
			$res["comp_mob1"] = $row["comp_mob1"];
			$res["comp_person"] = $row["comp_person"];
			$res["comp_licno"] = $row["comp_licno"];
			
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