<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
	<h3>Rechercher un membre du personnel</h3>
	<div style="margin-top:5%;margin-bottom:1.5%">
		<form action="search" method="post"
			class="form-inline align-items-center">
			<div class="col-sm">
				<fieldset class="form-group">
					<input type="text" value="<c:out value='${user.name}' />"
						class="form-control" name="nom" placeholder="Par Nom">
				</fieldset>
			</div>
			<div class="col-sm">
				<fieldset class="form-group">
					<input type="text" value="<c:out value='${user.email}' />"
						class="form-control" name="email" placeholder="Par Email">
				</fieldset>
			</div>
			<div class="col-sm">
				<fieldset class="form-group">
					<input type="text" value="<c:out value='${user.pays}' />"
						class="form-control" name="pays" placeholder="Par Pays">
				</fieldset>
			</div>
			<button type="submit" class="btn btn-primary">Rechercher</button>

		</form>
	</div>
</div>


