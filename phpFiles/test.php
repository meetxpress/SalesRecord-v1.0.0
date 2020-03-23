<?php
    $con = mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());  

    //$qry0=mysqli_query($con,"select SUBSTRING(currDate,4) as subStr from leave_management where emp_id = '".$_GET['emp_id']."'");
    $qry=mysqli_query($con,"select * from leave_management where emp_id = '".$_GET['emp_id']."' and status='Approved'");
        if(mysqli_num_rows($qry0) > 0 ){ 
            $count=0;
            while($row = mysqli_fetch_assoc($qry0)){
                $count++;
            }
            echo $count;
        }
        $count=0;
?>