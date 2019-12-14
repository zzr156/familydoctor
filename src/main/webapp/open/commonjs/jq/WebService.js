
var WSDLS = {};

var WebService = new Class({

	url : '',
	method : '',
	options: 
	{
		method:'GET',
		data: null,
		update: null,
		onComplete: Class.empty,
		onError:Class.empty,
		evalScripts: false,
		evalResponse: false
	},
	
	initialize: function(url,method,options)
	{
		this.url = url;
		this.method = method;
		this.options = options;

	},
	
	request : function()
	{
		var wsdl = WSDLS[this.url];
		if(!wsdl) 
		{
			var op = {method:'GET',async: false};
			var wsdlAjax = new XHR(op).send(this.url + "?wsdl", null);			
			wsdl = wsdlAjax.transport.responseXML;
			WSDLS[this.url] = wsdl;
		}
		this.setSoap(wsdl);
	},
		
	setSoap : function(wsdl)
	{
		
		var ns = (wsdl.documentElement.attributes["targetNamespace"] + "" == "undefined") ? wsdl.documentElement.attributes.getNamedItem("targetNamespace").nodeValue : wsdl.documentElement.attributes["targetNamespace"].value;
		var sr = 
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<soap:Envelope " +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
				"xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
				"<soap:Body>" +
				"<" + this.method + " xmlns=\"" + ns + "\">" +
					 (this.options.data === null ?"":this.options.data) +
				"</" + this.method + "></soap:Body></soap:Envelope>";
		
		this.options.method = 'post';
		this.options.data = null;
		
		var soapaction = ((ns.lastIndexOf("/") != ns.length - 1) ? ns + "/" : ns) + this.method;
		
		var soapAjax = new Ajax(this.url,this.options);
		soapAjax.setHeader("SOAPAction", soapaction);
		soapAjax.setHeader("Content-type", "text/xml; charset=utf-8");
		soapAjax.request(sr);
	}
	
});
