<?php
    $res = array();
    $c0=0;
	if(isset($_POST['comp_id']) && ($_POST['yr'])){
        $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  
        $qry0=mysqli_query($con,"select * from employee_master where comp_id = '".$_POST['comp_id']."'");
        if(mysqli_num_rows($qry0) > 0 ){ 
            while($row0 = mysqli_fetch_assoc($qry0)){
                $c0++;
                $eid = $row0['emp_id'];
                
                //$qry=mysqli_query($con,"select rt_date, SUM(rt_unit) as totSales from emp_routinetask where emp_id = '$eid'");
                
                $qry=mysqli_query($con,"select * from emp_routinetask where emp_id = '$eid'");
                if($qry){  
                    $res["Sales"] = array();    
                    while($row1 = mysqli_fetch_assoc($qry)){
                        $duh = $row1['rt_date'];
                        $mon = substr($duh, 3);
                        $unit = $row1['rt_unit'];
                        //echo "<h1>$mon<br>".$_POST['yr']."</h1>";
                         $totSales =0; 
                        $sales = array();                           
                        if($_POST['yr'] == $mon){   
                           
                            $totSales = $totSales + $unit;
                            $sales["totSales"] = $totSales;//$row1["totSales"];
                            $sales["mon"] = $mon;
        
                            $res["success"] = 1;
                            $res["message"] = "Sales found Successfully.";
                            
                            array_push($res["Sales"], $sales);  
                            echo json_encode($res);                                  
                        }                                                            
                    }
                } else {
                    $res["success"] = 0;					
                    $res["message"] = "No data found";
                             
                    echo json_encode($res);
                } 
                //echo "<br>Units: ".$sales["totSales"]."<br>Month: ". $mon ."<br>";
            }
        }
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Fields are missing.";
		
		echo json_encode($res);
	}
?>