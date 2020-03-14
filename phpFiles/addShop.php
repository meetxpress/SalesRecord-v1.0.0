<?php
	$res = array();
    if(isset($_POST['comp_id']) && ($_POST['sName']) && ($_POST['sLocLat']) && ($_POST['sLocLong'])) { 
        //?comp_id=100041&shop_id=200001&sName=Mayuri Mobile&sLoc=123.123123123123123
	
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		$qry = mysqli_query($con, "insert into shop_master(comp_id, shop_name, shop_LocLat, shop_LocLong) values('".$_POST['comp_id']."', '".$_POST['sName']."', '".($_POST['sLocLat'])."', '".($_POST['sLocLong'])."')");

		if($qry){
			$res["success"] = 1;
			$res["message"] = "Shop registered Successfully.";
			
			echo json_encode($res);
		}else{
			$res["success"] = 0;
			$res["message"] = "Can't Regsiter the shop.";
				
			echo json_encode($res);
		}		
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>