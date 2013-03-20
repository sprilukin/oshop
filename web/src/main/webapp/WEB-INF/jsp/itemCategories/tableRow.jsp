<tr>
    <td>{{id}}</td>
    <td>{{name}}</td>
    <td>
        <div class="btn-group">
            <button class="btn btn-mini btn-info dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu">
                <li data-action="modify" data-id="{{id}}"><a tabindex="-1" href="#"><i class="icon-pencil"></i> <spring:message code="general.modify"/></a></li>
                <li data-action="delete" data-id="{{id}}"><a tabindex="-1" href="#"><i class="icon-trash"></i> <spring:message code="general.remove"/></a></li>
            </ul>
        </div>
    </td>
</tr>
