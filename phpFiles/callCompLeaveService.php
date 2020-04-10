<?php
    $res = array();
    $c0=0;
    $count0=0;
    $count1=0;
	
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  					
        $qry0=mysqli_query($con,"select * from employee_master where comp_id = '".$_POST['comp_id']."'");         			
					
		if($qry0){ 				
            $res["success"] = 1;
            $res["message"] = "Leaves found Successfully.";
            $res["Leaves"] = array();

            while($row0 = mysqli_fetch_assoc($qry0)){
                $eid = $row0['emp_id'];                
               //echo "<h3>Emp_id: ".$eid."</h3>";
                                
                $qry=mysqli_query($con,"select * from leave_management where emp_id = '$eid'");                
                if($qry){                  				
                    while($row1 = mysqli_fetch_assoc($qry)){
                        $count0++;
                        //echo "<br>Count0: ".$count0;
                    }
                    
                    $qry2=mysqli_query($con,"select * from leave_management where emp_id = '$eid' and status='Approved'");                
                    if($qry2){                  				
                        while($row2 = mysqli_fetch_assoc($qry2)){
                            $duh = $row2['fromDate'];
                            $mon = substr($duh, 3);					
                            if($mon == $_POST['yr']){
                                $count1++;
                                //echo "<br>Count1: ".$count1."<br>";
                            }  
                        }
                        $sal = array(); 
                        $sal["emp_id"] = $eid;                        
                        $sal["l_count"] = $count0;
                        $sal["a_count"] = $count1;
                        array_push($res["Leaves"], $sal);                                                      
                    }                                                                                                      
                    $count0=0;
                    $count1=0;     									
                } else {
                    $res["success"] = 0;					
                    $res["message"] = "No data found";
                             
                    echo json_encode($res);
                }        
            }
            echo json_encode($res);
        }
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Fields are missing.";
		
		echo json_encode($res);
	}
?>