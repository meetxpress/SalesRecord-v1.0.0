<?php
    $res = array();
    $c0=0;
    $count0=0;
    $count1=0;
	
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  					
        $qry0=mysqli_query($con,"select * from employee_master where comp_id = '".$_POST['comp_id']."'");         			
					
		if($qry0){ 				            
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
                    
                    $qry2=mysqli_query($con,"select * from leave_management where emp_id = '$eid' and status='Approved' "); 
                    if($qry2){                  				
                        while($row2 = mysqli_fetch_assoc($qry2)){
                            $duh = $row2['fromDate'];
                            $mon = substr($duh, 3);	

                            if($mon == $_POST['yr']){
                                $count1++;
                                //echo "<br>Count1: ".$count1."<br>";
                            }
                            //echo "<br>Count1: ".$count1."<br>".$_POST['yr']."<br>".$duh;
                        }
                        $Leaves = array(); 
                        $Leaves["emp_id"] = $eid;                        
                        $Leaves["l_count"] = $count0;
                        $Leaves["a_count"] = $count1;
                        array_push($res["Leaves"], $Leaves);    
                        
                        $res["success"] = 1;
                        $res["message"] = "Leaves found Successfully.";
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