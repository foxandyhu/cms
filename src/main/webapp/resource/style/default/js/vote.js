Cms.vote = {
    init:function(voteTopicId){
        var vote=this;
        $("#submitVoteBtn").bind("click",function () {
            vote.submit(voteTopicId);
        });
        $(".result").bind("click",function () {
            vote.showResult(voteTopicId);
        });
    },
    showResult: function (topicId) {
        $("#submitVoteBtn").hide();
        Cms.util.httpGet("/vote/result/id_"+topicId+".html",null,function(response){
            if(response && response.message){
                var data=response.message;
                var index=0;
                $.each(data,function(key,value){
                    var id = "result_" + key;
                    $("#" + id).html("");
                    $(".vote_list .answer").hide();
                    $("#" + id).poll("poll"+index, {
                        title:"",
                        titleColor: '#ff6600',
                        width: '100%',
                        bgImg:Cms.config.resServer+'/style/default/vote_bar_bg.gif',
                        data: value
                    });
                    index++;
                });
            }
        });
    },
    submit:function(voteTopicId){
        var subTopics=$(".subTopic");
        var data=new Array();
        if(subTopics){
            $.each(subTopics,function (index,subTopic) {
                var subTopicId=$(subTopic).val();
                var voteItems=$(".answerItem"+subTopicId);
                var json={subTopicId:subTopicId,items:[],text:''};
                if(voteItems){
                    $.each(voteItems,function(index,voteItem){
                        var item=$(voteItem);
                        var itemId=item.attr("data-id");
                        if(item.is("textarea")){
                            json.text=item.val();
                        }else{
                            if(item.is(":checked")){
                                json.items.push(itemId);
                            }
                        }
                    });
                    data.push(json);
                }
            });
            Cms.util.httpPost("/vote/submit/"+voteTopicId+".html",JSON.stringify(data),function(){
                $(".result").click();
            },null,function(response){
                if(response.status===401){
                    Cms.common.login();
                }else{
                    Cms.dialog.error(response.responseText);
                }
            },null,"application/json;charset=UTF-8");
        }
    }
}
