<?php
// session_unregister()
session_start();
    unset($_SESSION['loginid']);
    unset($_SESSION['username']);
    session_destroy();
    header('Location: index.php');

?>
