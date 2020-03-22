<?php
    $res = array();
	if(isset($_POST['comp_id'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  

        //getting company id using emp_id and assigning to variable
        $qry1=mysqli_query($con,"select emp_id from employee_master where comp_id = '".$_POST['comp_id']."'");
        if(mysqli_num_rows($qry1) > 0 ){ 
            $row1 = mysqli_fetch_assoc($qry1);
        }
        $emp_id = $row1['emp_id'];

        //fetching data from the main table
        $qry2 = mysqli_query($con,"select * from leave_management where status='Pending'");
		if($qry2){			
            $res["success"] = 1;
            $res["message"] = "Leaves found Successfully.";
            //creating json array to store json in array format
            $res["Leave"] = array();

            while($row = mysqli_fetch_assoc($qry2)){
                $lv = array();

                $lv["emp_id"] = $row["emp_id"];
                $lv["leave_id"] = $row["leave_id"];
                $lv["fromDate"] = $row["fromDate"];
                $lv["toDate"] = $row["toDate"];
                $lv["type1"] = $row["type1"];
                $lv["type2"] = $row["type2"];		
                $lv["reason"] = $row["reason"];			
                $lv["status"] = $row["status"];	

                array_push($res["Leave"], $lv);
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