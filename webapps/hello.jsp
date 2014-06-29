<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>jQuery UI Menu - Default functionality</title>
    <link href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" rel="stylesheet" />
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
    <script type="text/javascript">
        $(function() {
            $( "#menu" ).menu({
                position: {at: "left bottom"}
            });
        });
    </script>
    <style type="text/css">
        .ui-menu { overflow: hidden;}
        .ui-menu .ui-menu { overflow: visible !important; }
        .ui-menu > li { float: left; display: block; width: auto !important; }
        .ui-menu > li { margin: 5px 5px !important; padding: 0 0 !important; }
        .ui-menu > li > a { float: left; display: block; clear: both; overflow: hidden;}
        .ui-menu .ui-menu-icon { margin-top: 0.3em !important;}
        .ui-menu .ui-menu .ui-menu li { float: left; display: block;}
    </style>
</head>
<body>
 
<ul id="menu">
    <li class="ui-state-disabled"><a href="#">Aberdeen</a></li>
    <li><a href="#">Ada</a></li>
    <li><a href="#">Adamsville</a></li>
    <li><a href="#">Addyston</a></li>
    <li>
        <a href="#">Delphi</a>
        <ul>
            <li class="ui-state-disabled"><a href="#">Ada</a></li>
            <li><a href="#">Saarland</a></li>
            <li><a href="#">Salzburg</a></li>
        </ul>
    </li>
    <li><a href="#">Saarland</a></li>
    <li>
        <a href="#">Salzburg</a>
        <ul>
            <li>
                <a href="#">Delphi</a>
                <ul>
                    <li><a href="#">Ada</a></li>
                    <li><a href="#">Saarland</a></li>
                    <li><a href="#">Salzburg</a></li>
                </ul>
            </li>
            <li>
                <a href="#">Delphi</a>
                <ul>
                    <li><a href="#">Ada</a></li>
                    <li><a href="#">Saarland</a></li>
                    <li><a href="#">Salzburg</a></li>
                </ul>
            </li>
            <li>
                <a href="#">Perch</a>
                <ul>
                    <li><a href="#">Ada</a></li>
                    <li><a href="#">Saarland</a></li>
                    <li><a href="#">Salzburg</a></li>
                </ul>
            </li>
        </ul>
    </li>
    <li class="ui-state-disabled"><a href="#">Amesville</a></li>
</ul>
 
</body>
</html> 
