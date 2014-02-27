<%@ page import="com.ee.ff.Gg" %>



<div class="fieldcontain ${hasErrors(bean: ggInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="gg.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${ggInstance?.name}"/>
	<%-- <g:textArea name="myField" value=""/>--%>
	 <g:submitButton name="save" value="save" />
</div>

