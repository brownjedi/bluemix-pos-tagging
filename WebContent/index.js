function generatePOS() {
	var sentence = $('#comment').val();

	if(sentence) {
		var request = $.post('api/pos', {"sentence": sentence});
		$.blockUI({
			message: '<div class="disable-click-events">' +
			'<embed type="image/svg+xml" src="images/loading-indicator.svg" class="wait-svg disable-click-events">' +
			'</div>'
			, css: {
				'border': 'none',
				'backgroundColor': 'transparent !important',
				'background-color': 'transparent !important'
			}
		}); 

		request.success(function (parsedTokens) {
			var html = '';
			for (var i = 0; i < parsedTokens.length; i++) {
				var parsedToken = parsedTokens[i];
				html = html + '<span class="span">';
				html = html + '<span class="label ' + parsedToken.tag + '">' + parsedToken.tag + '</span>';
				html = html + '<span class="token ' + parsedToken.tag + '">' + parsedToken.token + '</span>';
				html = html + '</span>';
			}

			$('#output').html(html);
			$('#outputDiv').show();
			$.unblockUI();
		});

		request.fail(function() {
			$.unblockUI();
		});
	} else {
		$('#outputDiv').hide();
	}
}