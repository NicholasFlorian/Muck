package prayhard.muck;

import java.util.Date;


public class Mish {

    //meta data
    private Date TimeCreated;
    private Date TimeLastEdited;
    private Date TimeToCompleteBy;
    private Date TimeCompletedOn;
    private String CreatorID;
    private String OnlineID;

    //functional data
    private String Title;
    //TODO create a vector of bodies
    private String Body;
    private boolean HasDate;
    private boolean IsReminder;
    private boolean IsComplete;

    public Mish() {

        this.TimeCreated        = new Date(0);      //create new dates as the current time
        this.TimeLastEdited     = new Date(0);      //create new dates as the current time
        this.TimeToCompleteBy   = new Date(0);      //set
        this.TimeCompletedOn    = new Date(0);      //set to 0 as encoded incomplete- see IsComplete

        //more meta data
        this.CreatorID = "";
        //TODO request online access create OnlineID
        this.OnlineID = "";

        //functional data
        this.Title      = "";
        this.Body       = "";
        this.HasDate    = false;
        this.IsReminder = false;
        this.IsComplete = false;

    }


    public Mish(Date TimeToCompleteBy, String CreatorID, String Title, String Body, boolean HasDate, boolean IsReminder) {

        this.TimeCreated        = new Date();           //create new dates as the current time
        this.TimeLastEdited     = new Date();           //create new dates as the current time
        this.TimeToCompleteBy   = TimeToCompleteBy;     //set
        this.TimeCompletedOn    = new Date(0);          //set to 0 as encoded incomplete- see IsComplete

        //more meta data
        this.CreatorID = CreatorID;
        //TODO request online access create OnlineID
        this.OnlineID = "";

        //functional data
        this.Title      = Title;
        this.Body       = Body;
        this.HasDate    = HasDate;
        this.IsReminder = IsReminder;
        this.IsComplete = false;

    }
    public String toString(){

        //var
        String Out;


        Out = new String("MISH:\n");

        //assign
        Out = Out.concat("CREATED_ON: " + TimeCreated.toString() + "\n");
        Out = Out.concat("LAST_EDITED: " + TimeLastEdited.toString() + "\n");
        Out =  Out.concat("COMPLETE_BY: " + TimeToCompleteBy.toString() + "\n");
        Out =  Out.concat("COMPLETED_ON: " + TimeCompletedOn.toString() + "\n\n");

        Out =  Out.concat("USER_ID: " + CreatorID.toString() + "\n\n");
        //Todo do the online id

        Out =  Out.concat("TILE: " + Title.toString() + "\n");
        Out =  Out.concat("BODY: " + Body.toString() + "\n");
        Out =  Out.concat("HAS_DATE: " + HasDate + "\n");
        Out =  Out.concat("IS_REMINDER: " + IsReminder + "\n");
        Out =  Out.concat("IS_COMPLETE: " + IsComplete + "\n");


        return Out;

    }


    //Accessor Functions
    public Date GetTimeCreated(){

        return this.TimeCreated;
    }

    public Date GetTimeLastEdited(){

        return this.TimeLastEdited;
    }

    public Date GetTimeToCompletedBy(){

        return this.TimeToCompleteBy;
    }

    public Date GetTimeCompletedOn(){

        return this.TimeCompletedOn;
    }

    public String GetCreatorId(){

        return this.CreatorID;
    }

    public String GetOnlineId(){

        return this.OnlineID;
    }

    public String GetTitle() {

        return this.Title;
    }

    public String GetBody(){

        return this.Body;
    }

    public boolean GetHasDate(){

        return this.HasDate;
    }

    public boolean GetIsReminder(){

        return this.IsReminder;
    }

    public boolean GetIsComplete(){

        return this.IsComplete;
    }

    //Mutator Functions
    public void SetTimeToCompleteBy(Date TimeToCompleteBy){

        //update last edited time
        this.TimeLastEdited = new Date();

        this.TimeToCompleteBy = TimeToCompleteBy;
    }

    public void SetTitle(String Title){

        //update last edited time
        this.TimeLastEdited = new Date();

        this.Title = Title;
    }

    public void SetBody(String Body){

        //update last edited time
        this.TimeLastEdited = new Date();

        this.Body = Body;
    }

    public void SetIsReminder(boolean IsReminder){

        //update last edited time
        this.TimeLastEdited = new Date();

        this.IsReminder = IsReminder;
    }

    public void SetHasDate(boolean HasDate){

        //update last edited time
        this.TimeLastEdited = new Date();

        this.HasDate = HasDate;
    }

    public void SetAsComplete(){

        //var
        Date mDate = new Date();

        //update last edited time
        this.TimeLastEdited = mDate;
        this.TimeCompletedOn = mDate;
    }

    public void UpdateCompletion(Boolean IsComplete){

        //update completion time
        if(!this.IsComplete && IsComplete){

            this.TimeCompletedOn = new Date();

        }

        //update last edited time
        this.TimeLastEdited = this.TimeCompletedOn; //keep these times the same

        this.IsComplete = IsComplete;
    }

    public boolean UpdateOnlineID(String OnlineID){

        //set only if not set yet
        if(this.OnlineID.equals("")){

            this.OnlineID = OnlineID;

            return true;
        }

        return false;
    }



}

