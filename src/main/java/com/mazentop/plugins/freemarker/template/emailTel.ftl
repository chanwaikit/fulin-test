<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" content="text/html;charset=UTF-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>邮件模板</title>
    <style>
        ul{
            padding: 0;
        }
        ul li{
            list-style: none;
        }
        .clearfix:before, .clearfix:after {
            content: ".";
            display: table;
            height: 0;
            font-size: 0;
            line-height: 0;
            visibility: hidden;
            clear: both;
        }
        .email-body{
            max-width: 1200px;
            padding: 40px 20px 0 20px;
            -webkit-box-shadow: 2px 3px 6px #00000030;
            box-shadow: 2px 3px 6px #00000030;
            padding: 20px;
        }
        .email-header,.email-main,.email-footer{
            margin:0 0 20px 0
        }
        .email-title{
            font-size: 28px;
            line-height: 30px;
            padding-bottom: 30px;
            font-weight: bold;
        }
        .email-name{
            font-size: 18px;
            line-height: 28px;
            padding-bottom: 20px;
        }
        .email-content{
            line-height: 28px;
            padding-bottom: 20px;
        }
        .email-logo{
            border-radius: 4px 4px 0 0;
            background-color: #F8F9FA;
            text-align: center;
            padding: 17px 0;
            margin-bottom: 10px;
        }
        .email-order-title{
            padding: 20px 0 30px 0;
            border-bottom: 1px solid #9a9797;
        }
        .email-order-method{
            /*display: flex;*/
            padding: 20px 0 30px;
        }
        .email-order-method .email-method{
            width: 50%;
            float: left;
        }
        .email-method .email-method-title{
            font-size: 18px;
            padding-bottom: 10px;
            font-weight: bold;
        }
        .email-method p{
            line-height: 30px;
            margin: 0;
            padding: 0;
        }
        .email-order-main{
            padding: 20px 0 0 0;
        }
        .email-order-table{
            width: 60%;
        }
        .email-order-table tr{
            line-height: 30px;
        }
        .email-order-table tr p{
            margin: 0 0 10px 0;
        }
        .email-text{
            text-align: center;
        }
        .email-bold{
            font-weight: bold;
        }
        @media (max-width: 768px){
            .email-order-table{
                width: 100%;
            }
        }
        @media (max-width: 414px){
            .email-order-method .email-method{
                width: 100%;
                margin-bottom: 20px;
            }
            .email-title{
                font-size: 22px;
            }
        }
    </style>
</head>
<body>
    <div class="email-body">
        <div class="email-header">
            <div class="email-title">Dear Customer</div>
            <div class="email-name">${order.receiverName}</div>
            <div class="email-content">
                Thank you for your order from Frangipani. Once your package ships we will send you a tracking number.
                You can check the status of your order by logging into your account. If you have questions about your order,
                you can email us at admin@frangifashion.com or call us at 61619099. Our hours are 10 am - 9 pm.
            </div>
        </div>
        <div class="email-logo">
            <strong>MAZENTOP</strong>
        </div>
        <div class="email-main">
            <div class="email-order-title">
                <div class="email-title">
                    <span>Your order</span>
                    <span>${order.orderNo}</span>
                </div>
                <div>
                    ${order.emailSendTime}
                </div>
            </div>
            <div class="email-order-method clearfix">
                <div class="email-method">
                    <p class="email-method-title">Shipping Address</p>
                    <p>
                        <span>Country:</span>
                        <span>${order.receiverCountry}</span>
                    </p>
                    <p>
                        <span>province:</span>
                        <span>${order.receiverProvince}</span>
                    </p>
                    <p>
                        <span>City:</span>
                        <span>${order.receiverCity}</span>
                    </p>
                    <p>
                        <span>Address:</span>
                        <span>${order.receiverAddress}</span>
                    </p>
                    <p>
                        <span>Email:</span>
                        <span>${order.receiverEmail}</span>
                    </p>
                    <p>
                        <span>Postcode:</span>
                        <span>${order.receiverPost}</span>
                    </p>
                    [#if order.receiverPhone??]
                    <p>
                        <span>Postcode:</span>
                        <span>${order.receiverPhone}</span>
                    </p>
                    [/#if]
                </div>
                <div class="email-method">
                    <p class="email-method-title">Billing Address</p>
                    <p>
                        <span>Country:</span>
                        <span>${order.invoiceCountry}</span>
                    </p>
                    <p>
                        <span>province:</span>
                        <span>${order.invoiceProvince}</span>
                    </p>
                    <p>
                        <span>City:</span>
                        <span>${order.invoiceCity}</span>
                    </p>
                    <p>
                        <span>Address:</span>
                        <span>${order.invoiceAddress}</span>
                    </p>
                    <p>
                        <span>Email:</span>
                        <span>${order.invoiceEmail}</span>
                    </p>
                    <p>
                        <span>Postcode:</span>
                        <span>${order.invoicePost}</span>
                    </p>
                    [#if order.invoicePhone??]
                    <p>
                        <span>Phone:</span>
                        <span>${order.invoicePhone}</span>
                    </p>
                    [/#if]
                </div>
            </div>
            <div class="email-order-method clearfix">
                <div class="email-method">
                    <div>
                        <p class="email-method-title">Payment Method</p>
                        <p>${order.paymentMethod}</p>
                    </div>
                </div>
                <div class="email-method">
                    <div>
                        <p class="email-method-title">Logistics Method</p>
                        <p>no information</p>
                    </div>
                </div>
            </div>
            <hr />
            <div class="email-order-main">
                <table class="email-order-table">
                    <tbody>
                    <tr>
                        <th style="width: 60%">Items</th>
                        <th>Qty</th>
                        <th>Price</th>
                    </tr>
                    [#list order.list as item]
                    <tr>
                        <td>
                            <p>${item.productName}</p>
                            <p>SKU: ${item.productSku}</p>
                            [#if item.spec??]
                                <p>${item.sepc}</p>
                            [/#if]
                        </td>
                        <td class="email-text">${item.productNum}</td>
                        <td class="email-text">${item.productMallPrice} ${order.currency}</td>
                    </tr>
                    [/#list]
                    <tr style="">
                        <td class="email-bold">Subtotal</td>
                        <td></td>
                        <td class="email-text">${order.productTotalPrice} ${order.currency}</td>
                    </tr>
                    <tr>
                        <td class="email-bold">Product Discount Price</td>
                        <td></td>
                        <td class="email-text">${order.productDiscountPrice} ${order.currency}</td>
                    </tr>
                    <tr>
                        <td class="email-bold">Tax Rate</td>
                        <td></td>
                        <td class="email-text">${order.taxRate?default("0")}% ${order.currency}</td>
                    </tr>
                    <tr>
                        <td class="email-bold">Tax Amount</td>
                        <td></td>
                        <td class="email-text">${order.taxAmount} ${order.currency}</td>
                    </tr>
                    <tr>
                        <td class="email-bold">Shipping & Handling</td>
                        <td></td>
                        <td class="email-text">${order.totalPaymentFreetotalTransportsFree?default("0.00")} ${order.currency}</td>
                    </tr>
                    <tr>
                        <td class="email-bold">Grand Total</td>
                        <td></td>
                        <td class="email-text email-bold">${order.totalPaymentFree} ${order.currency}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="email-footer">Thank you, Frangipani!</div>
    </div>
</body>
</html>