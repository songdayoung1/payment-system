// PortOne SDK 초기화
const getPortOne = window.PortOne || null;


if (!getPortOne) {
    console.error('PortOne SDK가 로드되지 않았습니다.');
}

    // KG 결제 시 필요한 고객 정보
    customer = {
        customerId: 'cid-' + "id01",
        fullName : "song",
        phoneNumber : "010-9757-9002",
        email : "gkrba1234@gmail.com",
    }

    // KG payment 파라미터
    const paymentKG = {
      storeId: "store-e19395f7-b747-4b49-b4ef-c8ab3c0ea19b",
      paymentId: "paymentId_25",
      orderName: "나이키 와플 트레이너 2 SD",
      totalAmount: 1000,
      currency: "CURRENCY_KRW",
      channelKey: "channel-key-0387c126-edc9-412e-9f3f-df56eb06ae00",
      payMethod: "CARD",
      customer : customer
    }

    // 토스 payment 파라미터
    const paymentToss = {
      storeId: "store-e19395f7-b747-4b49-b4ef-c8ab3c0ea19b",
      paymentId: "paymentId_02",
      orderName: "paymentId_02의 상품",
      totalAmount: 1000,
      currency: "KRW",
      channelKey: "channel-key-2192d557-5254-49d6-8ad9-a7516f3918c0",
      payMethod: "CARD",
    }


// 결제 요청 함수
async function requestPayment() {
    try {
        const response = await getPortOne.requestPayment(paymentKG);

        // 오류 발생
        if (response.code !== undefined) {
            return alert(response.message);
        }

        console.log('response', response);


        const orderRequest = {
          userId: 1,
          paymentUid: response.txId,
          orderProducts: [
            {
              orderProductId: 101,
              quantity: 1,
              paymentPrice: 1000,
            },
          ],
          totalAmount: paymentKG.totalAmount,
        };

        const order = await fetch("http://localhost:8080/api/v2/orders", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify(orderRequest),
            })
            .then((data) => {
               console.log("Order created successfully:", data);
              })
            .catch((error) => {
               console.error("Error creating order:", error);
            });

        /*
        const notified = await fetch(`http://localhost:8080/api/v2/payment/callback`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                paymentId: paymentId,
            }),
        });
        */
    } catch (error) {
        console.error('결제 요청에 대한 에러 발생 ‼️', error);
        alert('결제 요청 중 오류가 발생했습니다.');
    }
}

const cancelPaymentId = "paymentId_03"
async function cancelPayment(paymentId) {
    const url = `https://api.portone.io/payments/${cancelPaymentId}/cancel`; // 요청 URL

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : 'PortOne jTqKFB9HcmesG6uS013OVpp9y9SEROkQvQdeiN2quPetO5NRAg4ZJZ2NePlxMmJdNbr9xEY9lhWIvMg8'
            },
            body: JSON.stringify({
                'reason' : "reason"
            }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error('결제 취소에 대한 에러 발생 ‼️', errorData);
            return alert(`결제 취소 실패: ${errorData.message}`);
        }

        const result = await response.json();
        console.log('결제 취소 성공 💡', result);
        alert('결제 취소 성공!');
    } catch (error) {
        console.error('요청 중 오류 발생 ‼️', error);
        alert('결제 취소 요청 중 오류가 발생했습니다.');
    }
}


// DOM 로드 후 작업
document.addEventListener('DOMContentLoaded', async () => {
    const payButton = document.getElementById('pay-button');
    const payCancelButton = document.getElementById('pay-cancel-button');

    if (payButton) {
        // 버튼 클릭 이벤트 추가
        payButton.addEventListener('click', () => {
            requestPayment()
        });
    } else {
        console.error('pay-button 요소를 찾을 수 없습니다.');
    }

    if(payCancelButton){
        payCancelButton.addEventListener('click', () => {
           cancelPayment()
        });
    }
});



/*
   [결제 요청 / 실패 응답값]

       KG 첫번째 결제 내역
       response = {
        "paymentId": "paymentId_01",
        "transactionType": "PAYMENT",
        "txId": "01936236-79f9-4bb2-27be-c7b3538a237f"
       }

       Toss 두번째 결제 내영
       response =  {
          "paymentId": "paymentId_02",
          "transactionType": "PAYMENT",
          "txId": "01936242-7003-5905-21ec-ed39908392f9"
       }

   {
      "paymentId": "paymentId_03",
      "transactionType": "PAYMENT",
      "txId": "01936373-da9d-9b32-a0d2-d55486d5bc9e"
  }
       KG 첫번째 결제 취소 응답값
       "cancellation": {
           "status": "SUCCEEDED",
           "id": "01936281-ca0e-c38f-4367-928649272479",
           "pgCancellationId": "StdpayCARDINIpayTest20241125162629309690",
           "totalAmount": 1000,
           "taxFreeAmount": 0,
           "vatAmount": 91,
           "reason": "reason",
           "cancelledAt": "2024-11-25T08:48:13Z",
           "requestedAt": "2024-11-25T08:48:13Z",
           "receiptUrl": "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=StdpayCARDINIpayTest20241125162629309690&noMethod=1",
           "trigger": "API"
           }
       }
*/

