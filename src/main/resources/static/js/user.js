let index ={
    init:function(){
        $("#btn-save").on("click",()=>{ //function(){} 을 사용하지 않고 ()=>{}를 사용하는 이유는 this를 바인딩하기 위해서이다.
            this.save();
        });
    },

    save:function(){
        //alert('user의 save함수가 호출됨';)
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        //console.log(data);

        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
        //ajax호출시 default가 비동기 호출
        //ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
        $.ajax({
            type:"POST",
            url:"/blog/api/user",
            data:JSON.stringify(data), //http body데이터
            contentType:"application/json; charset=utf-8", //body데이터가 어떤타입인디 (MIME)
            dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 응답은 문자열 (문자열 생긴것이 json이라면) => javascript 오브젝트로 변경해준다.
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
            console.log(resp);
            location.href="/blog";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();