<%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Accounting module.
 *
 *  Billing module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Billing module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Billing module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Add/Edit Account" otherwise="/login.htm"
	redirect="/module/accounting/account.list" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="../includes/nav.jsp" %>
<h2>
	<spring:message code="accounting.account.addedit" />
</h2>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message
			code="${error.defaultMessage}" text="${error.defaultMessage}" /> </span>
</c:forEach>
<spring:bind path="accountCommand">
	<c:if test="${not empty  status.errorMessages}">
		<div class="error">
			<ul>
				<c:forEach items="${status.errorMessages}" var="error">
					<li>${error}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
</spring:bind>
<form method="post" class="box">
	<table>
		<tr>
			<td><spring:message code="general.name" /></td>
			<td><spring:bind path="accountCommand.name">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		<tr>
			<td valign="top"><spring:message code="general.description" />
			</td>
			<td><spring:bind path="accountCommand.description">
					<input type="text" name="${status.expression}"
						value="${status.value}" size="35" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind></td>
		</tr>
		<tr>
			<td valign="top"><spring:message code="accounting.accountType" />
			</td>
			<td><form:select path="accountCommand.accountType">
					<form:option value="" label="--Please Select--"/>
					<form:options items="${accountTypes}" itemLabel="name" />
				</form:select> <form:errors path="accountCommand.accountType"  cssClass="error" /></td>
		</tr>
		
		<tr>
			<td valign="top"><spring:message code="accounting.parentAccount" />
			</td>
			<td><form:select path="accountCommand.parentAccountId">
				<form:option value="" label="--Please Select--"/>
				<form:options items="${listParents}" itemValue="id" itemLabel="name"/>
			</form:select>
			 <form:errors path="accountCommand.parentAccountId" cssClass="error" /></td>
		</tr>
		<tr>
			<td><spring:message code="general.retired" /></td>
			<td><form:radiobutton path="accountCommand.retired" value="false" />NO <form:radiobutton
					path="accountCommand.retired" value="true" />YES</td>
				 <form:errors path="accountCommand.retired" cssClass="error" /></td>
		</tr>
	</table>
	<br /> <input type="submit"
		value="<spring:message code="general.save"/>"> <input
		type="button" value="<spring:message code="general.cancel"/>"
		onclick="javascript:window.location.href='account.list'">
</form>
<%@ include file="/WEB-INF/template/footer.jsp"%>