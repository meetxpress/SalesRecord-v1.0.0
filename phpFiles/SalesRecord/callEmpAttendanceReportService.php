<?php
    $res = array();
	if(isset($_POST['emp_id']) && ($_POST['yr'])){ 
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        $qry=mysqli_query($con,"select *, count(*) as countAtt from attendance_management where emp_id = '".$_POST['emp_id']."'");
    
        $mon = substr($_POST['yr'], 0, 2);
        if (($mon % 2 != 0) && ($mon <= 7)) {
            // odd months till July(7)
            $tot_days = 31;
        } else if (($mon % 2 != 0) && ($mon > 7)) {
            //odd months after July(7)
            $tot_days = 30;
        } else if (($mon == 02) && (substr($_POST['yr'], -4) % 4 == 0)) {
            //leap year
            $tot_days = 29;
        } else if (($mon == 02 ) && (substr($_POST['yr'], -4) % 4 != 0)) {
            //february month
            $tot_days = 28;
        } else if (($mon % 2 == 0) && ($mon <= 6)) {
            // even months before July(6)
            $tot_days = 30;
        } else if (($mon % 2 == 0) && ($mon >= 8)) {
            //even months after July(6)
            $tot_days = 31;
        } else {
            echo "outof range input";
        }
        
        if(mysqli_num_rows($qry) > 0 ){ 
            //creating json array to store json in array format
            $res["Attendance"] = array();

            while($row = mysqli_fetch_assoc($qry)){
                $mon = $row['p_date'];
                $year = substr($mon, 3);

                $att = array();                
                $att["tot_days"] = $tot_days;
                $att["tot_att"] = $row["countAtt"];

                if($_POST['yr'] == $year){                    
                    $res["success"] = 1;
                    $res["message"] = "Attendance found Successfully.";
                    
                    array_push($res["Attendance"], $att);
                }else{
                    $res["success"] = 0;					
                    $res["message"] = "No Attendance found";
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