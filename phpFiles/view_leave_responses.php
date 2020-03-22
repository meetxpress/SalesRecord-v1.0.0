<?php
    $res = array();
	if(isset($_POST['emp_id'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  

        //fetching data from the main table
        $qry = mysqli_query($con,"select * from leave_management where emp_id='".$_POST['emp_id']."'");
		if($qry){			
            $res["success"] = 1;
            $res["message"] = "Leaves found.";
            //creating json array to store json in array format
            $res["Leave"] = array();

            while($row = mysqli_fetch_assoc($qry)){
                $lvr = array();

                $lvr["leave_id"] = $row["leave_id"];
                $lvr["fromDate"] = $row["fromDate"];
                $lvr["toDate"] = $row["toDate"];
                $lvr["type1"] = $row["type1"];
                $lvr["type2"] = $row["type2"];		
                $lvr["reason"] = $row["reason"];			
                $lvr["status"] = $row["status"];	
               
                array_push($res["Leave"], $lvr);
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