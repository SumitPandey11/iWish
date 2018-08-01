function addGiftToEvent(giftId, eventId){

    $.get('http://localhost:8080/gift/'+giftId+'/addtoevent/'+eventId, function(data){

      $('#addGiftToEvent_btn_'+giftId+'_'+eventId).css("display","none");
      $('#deleteGiftFromEvent_btn_'+giftId+'_'+eventId).css("display","block");

    });

}

function deleteGiftFromEvent(giftId, eventId){

    $.get('http://localhost:8080/gift/'+giftId+'/deletefromevent/'+eventId, function(data){
      $('#addGiftToEvent_btn_'+giftId+'_'+eventId).css("display","block");
      $('#deleteGiftFromEvent_btn_'+giftId+'_'+eventId).css("display","none");

    });

}

