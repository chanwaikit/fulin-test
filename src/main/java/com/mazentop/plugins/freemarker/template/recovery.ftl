<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>email</title>
    <style>
        .email-content {
            margin: 40px 20px 0 20px;
            -webkit-box-shadow: 2px 3px 6px #00000030;
            box-shadow: 2px 3px 6px #00000030;
            padding: 20px 0;
            width: 30%;
        }

        .email-title {
            font-size: 16px;
            margin-bottom: 15px;
            margin-left: 20px;
        }

        .email-notification {
            color: #A7B1C2;
            margin-bottom: 20px;
            margin-left: 20px;
        }

        .email-logo {
            border-radius: 4px 4px 0 0;
            background-color: #F8F9FA;
            text-align: center;
            padding: 17px 0;
        }

        .order-main {
            padding: 0 20px;
        }

        .order-title {
            font-size: 16px;
            margin: 20px 0;
            text-align: center;
        }

        .order-notification {
            text-align: center;
            line-height: 20px;
            max-width: 350px;
            margin: 0 auto;
            color: #24323E;
        }

        .order-table {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
            padding: 20px 0;
            border-bottom: 1px solid #F6F6F7;
            border-top: 1px solid #F6F6F7;
        }

        .order-settlement {
            padding: 20px 0 15px 0;
            border-bottom: 2px dotted #F6F6F7;
        }

        .settlement-txt {
            width: 100%;
            height: auto;
            overflow: hidden;
            margin-bottom: 20px;
        }

        .settlement-left {
            float: left;
        }

        .settlement-right {
            float: right;
        }
    </style>
</head>
<body>
<div class="email-content">
    <div class="email-title">邮件通知</div>
    <div class="email-notification">欢迎加入MAZENTOP</div>
    <div class="email-logo">
        <strong>MAZENTOP</strong>
    </div>
    <div class="order-main">
        <div class="order-title">订单发货通知</div>
        <div class="order-notification">您的订单已发货啦快去看看到那里了！</div>
        <div style="text-align: center;">
            <a href="https://www.17track.net/zh-cn">点击此处</a>
        </div>
        <div class="order-settlement">
            <div class="settlement-txt">
                <div class="settlement-left">物流单号：</div>
                <div class="settlement-right">${transportsNo}</div>
                <div class="settlement-left">物流公司：</div>
                <div class="settlement-right">${transportsChannelName}</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>