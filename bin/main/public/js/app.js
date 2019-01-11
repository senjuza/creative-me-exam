
	$(function(){
        //init
		reload();
    	
    	//event listener
        $('#btSearch').on('click', function(){
        	reload();
        })
        
        $(document).on('keypress',function(e) {
            if(e.which == 13) {
            	reload();
            }
        });

    });

	//--------- function -----------
	var currentEventDtb;
	
    function getTblCurrentEvent(){
    	let url = "/data/";
    	currentEventDtb = $('#tbl-current-events').dataTable( {
            bProcessing: true,
            bServerSide: true,
            ajax: {
                url: url,
                type: "POST",
                crossDomain: true,
                dataType: "json",
                data: function ( d ) {
                    d.charName = $('#name').val();
                    d.charStatus = $('#status').val();
                    d.charGender = $('#gender').val();
                    d.charSpecies = $('#species').val();
                }
                
            },         
            paging:   true,
            ordering: false,
            bInfo:     false,
            bFilter: false,
            pageLength: 20,
            bLengthChange: false,
            bAutoWidth: false,
            columnDefs: [
                {targets: [ 0 ], visible: true, searchable: false, sortable: true, width: '10px', className: 'text-center'},
                {targets: [ 1 ], visible: true, searchable: true, sortable: true, width: '100px', className: 'text-center'},
                {targets: [ 2 ], visible: true, searchable: true, sortable: true, width: '400px', className: 'text-center'},
                {targets: [ 3 ], visible: true, searchable: true, sortable: true, width: '100px', className: 'text-center'},
                {targets: [ 4 ], visible: true, searchable: true, sortable: true, width: '150px', className: 'text-center'},
                {targets: [ 5 ], visible: true, searchable: true, sortable: true, width: '40px', className: 'text-center'},
                {targets: [ 6 ], visible: true, searchable: false, sortable: false, width: '180px', className: 'text-center'},
                {targets: [ 7 ], visible: true, searchable: false, sortable: false, width: '180px', className: 'text-center'}
                
            ],
            fnDrawCallback: function(oSettings){

                $('.cover-event').each(function(){
                    let coverURL = $(this).attr('src')
                    $('#'+$(this).attr('id'))
                     .load(function(){
                        //$('#result1').text('Image is loaded!');
                     })
                     .error(function(){
                        $(this).attr('src', 'https://dummyimage.com/400x520/000/fff.png&text=This+image+can+not+be+loaded.')
                     });
                })
            }
        }).api()   

        
    }
    
    function reload(){
    	if(currentEventDtb){
    		currentEventDtb.ajax.reload();
    		
    	}else{
    		getTblCurrentEvent();
    	}
    }

  