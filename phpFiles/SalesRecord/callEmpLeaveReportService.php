<?php
    $res = array();
	if(isset($_POST['emp_id']) && ($_POST['yr'])){
        $count1 = 0;
        $count0 = 0;

        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        /*getting leaveCount from database*/
        $qry0=mysqli_query($con,"select * from leave_management where emp_id = '".$_POST['emp_id']."'");
        if(mysqli_num_rows($qry0) > 0 ){ 
            while($row = mysqli_fetch_assoc($qry0)){
                $count0++;
            }
        }

        /*getting approved leave from database*/
        $qry=mysqli_query($con,"select * from leave_management where emp_id = '".$_POST['emp_id']."' and status='Approved'");
        if(mysqli_num_rows($qry) > 0 ){ 
            //creating json array to store json in array format
            $res["Leaves"] = array();
            
            while($row = mysqli_fetch_assoc($qry)){
                $mon = $row['currDate'];
                $year = substr($mon, 3);

                if($_POST['yr'] == $year){         
                    $count1++;                   
                }
            }
            if($count1 != 0 ){
                $res["success"] = 1;
                $res["message"] = "Leave found Successfully.";

                $lev = array();
                $lev["l_count"] = $count0;
                $lev["a_count"] = $count1;
                
                array_push($res["Leaves"], $lev);
                echo json_encode($res);
            }else{
                $res["success"] = 0;					
                $res["message"] = "No Leave found";

                echo json_encode($res);
            }             
            $count1 = 0;
            $count0 = 0;
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