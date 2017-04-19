<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<script src="/webjars/jquery/2.1.3/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/webjars/ace/01.08.2014/src-min-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
	<script src="/webjars/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	
	<link href="/webjars/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet" />
	
    <script>
    
    	$(document).ready(function(e) {
    		
    		var source = ace.edit("source");
    		source.setTheme("ace/theme/monokai");
    		source.getSession().setMode("ace/mode/java");
    		
    		var input = ace.edit("input");
    		input.setTheme("ace/theme/monokai");
    		input.getSession().setMode("ace/mode/java");
    		
    		var trace = ace.edit("trace");
    		trace.setTheme("ace/theme/monokai");
    		trace.getSession().setMode("ace/mode/text");
    		trace.setReadOnly(true);
    		
    		function appendMessage(message) {
    			
    			var current = trace.getSession().getValue();
    			
    			current += new Date() + '\n';
    			current += message;
    			current += '\n';
    			
    			trace.getSession().setValue(current);
    		}
    		
    		$('form').on('submit', function(e) {
    			e.preventDefault();
    			
    			trace.getSession().setValue('');
    			appendMessage('Editor> Build');
    			
    			$.ajax({
    				url: '/BuildServlet',
    				type: 'POST',
    				dataType: 'html',
    				data: {
    					source: source.getSession().getValue(),
    					input: input.getSession().getValue(),
    				}
    			})
    			.done(function(d) {
    				appendMessage('Server> \n' + d);
    			})
    			.fail(function(jqXHR, status, e) {
    				appendMessage('Editor> ' + e);
    			})
    		});
    	});
        
    </script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-xs-6">
				<form action="">
					<div class="form-group">
						<label for="source">Source</label>
						<div id="source" class="form-control" style="height: 500px">/* Hello World Example*/
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}</div>
					</div>
					<div class="form-group">
						<label for="source">Input</label>
						<div id="input" class="form-control" style="height: 100px"></div>
					</div>
					<div class="form-group">
						<input type="submit" value="Submit &amp; Run" class="btn btn-primary" />
					</div>
					
				</form>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label for="trace">Trace</label>
					<div id="trace" class="form-control" style="height: 500px"></div>
				</div>
			</div>
		</div>
	</div>
	

</body>
</html>