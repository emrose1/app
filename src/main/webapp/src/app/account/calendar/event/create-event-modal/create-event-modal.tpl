 <div class="form-horizontal" role="form">

  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Repeat:</label>
    <div class="col-sm-10">
      <select class="form-control">
        <option>Weekly</option>
      </select>
    </div>
  </div>

  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Repeat every:</label>
    <div class="col-sm-6">
      <div class="input-group">
        <input type="number" placeholder="1" class="form-control">
        <div class="input-group-btn">
          <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">Weeks <span class="caret"></span></button>
          <ul class="dropdown-menu pull-right">
            <li><a href="#">Weeks</a></li>
            <li><a href="#">Feet</a></li>
            <li><a href="#">mm</a></li>
          </ul>
        </div><!-- /btn-group -->
      </div>
    </div>
  </div>

  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Repeat on:</label>
    <div class="col-sm-10">
      <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox1" value="option1"> M
      </label>
      <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox2" value="option2"> T
      </label>
      <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox3" value="option3"> W
      </label>
      <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox3" value="option3"> T
      </label>
      <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox3" value="option3"> F
      </label>
      <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox3" value="option3"> S
      </label>
      <label class="checkbox-inline">
        <input type="checkbox" id="inlineCheckbox3" value="option3"> S
      </label>
    </div>
  </div>

  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Starts on:</label>
    <div class="col-sm-10">
      <input type="date" class="form-control" id="" placeholder="Start Date">
    </div>
  </div>

</div>

<div class="well">

  <div class="form-horizontal" role="form">
    <div class="form-group">
      <label for="inputPassword3" class="col-sm-2 control-label">Ends:</label>
      <div class="col-sm-10">
        <label class="radio-inline">
          <input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> Never
        </label>
      </div>
    </div>
  </div>

  <div class="col-sm-offset-2  form-group form-inline">
    <div class="form-group">
      <label class="radio-inline">
        <input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> After
      </label>
      <div class="input-group">
        <input type="number" class="form-control" id="" placeholder="number">
        <span class="input-group-addon">occurrences</span>
      </div>
    </div>
  </div>

  <div class="col-sm-offset-2  form-group form-inline">
    <div class="form-group">
      <label class="radio-inline">
        <input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> On
      </label>
      <input type="date" class="form-control" id="" placeholder="End Date">
    </div>
  </div>
</div>

<div class="col-sm-offset-2  form-group form-inline">
  <div class="form-group">
    <button type="submit" class="btn btn-default btn-block btn-primary">Save</button>
  </div>
  <div class="form-group">
    <button type="submit" class="btn btn-default btn-block btn-default">Cancel</button>
  </div>
</div>