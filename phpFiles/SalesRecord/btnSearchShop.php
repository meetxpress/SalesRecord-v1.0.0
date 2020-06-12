<?php
	$res = array();
	if(isset($_POST['shop_id']) && ($_POST['comp_id'])){

		$con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		$qry = mysqli_query($con,"select * from shop_master where shop_id = '".$_POST['shop_id']."' and comp_id='".$_POST['comp_id']."'");
		//$data  =  $con->query($qry);   //$con->query($sql);
		if(mysqli_num_rows($qry) > 0 ){
			$row = mysqli_fetch_assoc($qry);
			
			$res["success"] = 1;
			$res["message"] = "Shop found";
			
			$res["shop_name"] = $row["shop_name"];	
			
			echo json_encode($res);
		}else{
			$res["success"] = 0;					
			$res["message"] = "No Shop found";
					 
			echo json_encode($res);
		}		
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>