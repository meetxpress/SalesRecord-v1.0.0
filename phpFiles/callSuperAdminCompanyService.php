<?php
    $res=array();
    if(isset($_POST['uname'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        $qry=mysqli_query($con,"select * from company_master");

        if($qry){			
            $res["success"] = 1;
            $res["message"] = "Company found.";
            //creating json array to store json in array format
            $res["Comp"] = array();

            while($row = mysqli_fetch_assoc($qry)){
                $c = array();

                $c["comp_id"] = $row["comp_id"];
                $c["comp_name"] = $row["comp_name"];
                $c["comp_city"] = $row["comp_city"];
                $c["comp_mob1"] = $row["comp_mob1"];
                $c["comp_person"] = $row["comp_person"];
                $c["comp_status"] = $row["comp_status"];		

                array_push($res["Comp"], $c);
            }
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