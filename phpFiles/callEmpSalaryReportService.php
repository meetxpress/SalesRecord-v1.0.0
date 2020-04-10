<?php
    $res = array();
	if(isset($_POST['emp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        $qry=mysqli_query($con,"select * from emp_sal where emp_id = '".$_POST['emp_id']."'");
    
        if(mysqli_num_rows($qry) > 0 ){ 
            //creating json array to store json in array format
            $res["Salary"] = array();

            while($row = mysqli_fetch_assoc($qry)){
                $mon = $row['emp_month'];
                $year = substr($mon, 3);

                $sal = array();
                $sal["emp_inc"] = $row["emp_inc"];
                $sal["emp_totsal"] = $row["emp_totsal"];
                $sal["emp_month"] = $row["emp_month"];

                if($_POST['yr'] == $year){                    
                    $res["success"] = 1;
                    $res["message"] = "Salary found Successfully.";
                    
                    array_push($res["Salary"], $sal);
                }else{
                    $res["success"] = 0;					
                    $res["message"] = "No data found";
                }
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