package prayhard.muck;


import java.util.Calendar;

public class Mish {

    //meta data
    private Calendar TimeCreated;
    private Calendar TimeLastEdited;
    private Calendar TimeToCompleteBy;
    private Calendar TimeCompletedOn;
    private String CreatorID;
    private String UniversalID;

    //functional data
    private String Title;
    //TODO create a vector of bodies
    private String Body;
    private boolean HasDate;
    private boolean IsReminder;
    private boolean IsComplete;


    public Mish() {

        this.TimeCreated        = getEmptyCalendar();      //create new dates as the current time
        this.TimeLastEdited     = getEmptyCalendar();      //create new dates as the current time
        this.TimeToCompleteBy   = getEmptyCalendar();      //set
        this.TimeCompletedOn    = getEmptyCalendar();      //set to 0 as encoded incomplete- see IsComplete

        //more meta data
        this.CreatorID = "";

        //functional data
        this.Title      = "";
        this.Body       = "";
        this.HasDate    = false;
        this.IsReminder = false;
        this.IsComplete = false;

        //Set as empty
        this.UniversalID = "";

    }

    public Mish(Calendar TimeToCompleteBy, String CreatorID, String Title, String Body, boolean HasDate, boolean IsReminder) {

        this.TimeCreated        = Calendar.getInstance();   //create new dates as the current time
        this.TimeLastEdited     = Calendar.getInstance();   //create new dates as the current time
        this.TimeToCompleteBy   = TimeToCompleteBy;         //set
        this.TimeCompletedOn    = getEmptyCalendar();       //set to 0 as encoded incomplete- see IsComplete

        //more meta data
        this.CreatorID = CreatorID;

        //functional data
        this.Title      = Title;
        this.Body       = Body;
        this.HasDate    = HasDate;
        this.IsReminder = IsReminder;
        this.IsComplete = false;

        //createUniversalId
        this.UniversalID = generateUniversalID();
    }

    public Mish(String UniversalID, Calendar TimeToCompleteBy, String CreatorID, String Title, String Body, boolean HasDate, boolean IsReminder) {

        this.TimeCreated        = Calendar.getInstance();   //create new dates as the current time
        this.TimeLastEdited     = Calendar.getInstance();   //create new dates as the current time
        this.TimeToCompleteBy   = TimeToCompleteBy;         //set
        this.TimeCompletedOn    = getEmptyCalendar();       //set to 0 as encoded incomplete- see IsComplete

        //more meta data
        this.CreatorID = CreatorID;

        //functional data
        this.Title      = Title;
        this.Body       = Body;
        this.HasDate    = HasDate;
        this.IsReminder = IsReminder;
        this.IsComplete = false;

        //createUniversalId
        this.UniversalID = UniversalID;
    }

    public String toString(){

        //var
        String Out;

        //assign
        Out = new String("MISH:\n");
        Out = Out.concat("\tCREATED_ON: " + TimeCreated + "\n");
        Out = Out.concat("\tLAST_EDITED: " + TimeLastEdited.toString() + "\n");
        Out =  Out.concat("\tCOMPLETE_BY: " + TimeToCompleteBy.toString() + "\n");
        Out =  Out.concat("\tCOMPLETED_ON: " + TimeCompletedOn.toString() + "\n\n");
        Out =  Out.concat("\tUSER_ID: " + CreatorID.toString() + "\n\n");
        Out =  Out.concat("\tTILE: " + Title.toString() + "\n");
        Out =  Out.concat("\tBODY: " + Body.toString() + "\n");
        Out =  Out.concat("\tHAS_DATE: " + HasDate + "\n");
        Out =  Out.concat("\tIS_REMINDER: " + IsReminder + "\n");
        Out =  Out.concat("\tIS_COMPLETE: " + IsComplete + "\n\n");


        return Out;

    }

    private String generateUniversalID(){

        //var
        String Out;
        String Word;


        //assign
        if(Title.equals("") && !Body.equals(""))
            Word = Body;
        else if(Title.equals("") && Body.equals(""))
            Word = "/";
        else
            Word = Title;

        Out = "";
        Out = Out.concat(CreatorID + "." + Word + ".");
        Out = Out.concat(TimeCreated.get(Calendar.YEAR) + "");
        Out = Out.concat(TimeCreated.get(Calendar.MONTH)+ "");
        Out = Out.concat(TimeCreated.get(Calendar.DAY_OF_MONTH) +  "");
        Out = Out.concat(TimeCreated.get(Calendar.HOUR_OF_DAY) + "");
        Out = Out.concat(TimeCreated.get(Calendar.MINUTE) + "");
        /*Out = Out.concat(TimeToCompleteBy.get(Calendar.YEAR) + ".");
        Out = Out.concat(TimeToCompleteBy.get(Calendar.MONTH)+ ".");
        Out = Out.concat(TimeToCompleteBy.get(Calendar.DAY_OF_MONTH) +  ".");
        Out = Out.concat(TimeToCompleteBy.get(Calendar.HOUR_OF_DAY) + ".");
        Out = Out.concat(TimeToCompleteBy.get(Calendar.MINUTE) + ".");*/


        return Out;
    }

    //Accessor Functions
    public Calendar getTimeCreated(){

        return this.TimeCreated;
    }

    public Calendar getTimeLastEdited(){

        return this.TimeLastEdited;
    }

    public Calendar getTimeToCompleteBy(){

        return this.TimeToCompleteBy;
    }

    public Calendar getTimeCompletedOn(){

        return this.TimeCompletedOn;
    }

    public String getCreatorID(){

        return this.CreatorID;
    }

    public String getUniversalID(){

        return this.UniversalID;
    }

    public String getTitle() {

        return this.Title;
    }

    public String getBody(){

        return this.Body;
    }

    public boolean getHasDate(){

        return this.HasDate;
    }

    public boolean getIsReminder(){

        return this.IsReminder;
    }

    public boolean getIsComplete(){

        return this.IsComplete;
    }

    //Mutator Functions
    public void setTimeToCompleteBy(Calendar TimeToCompleteBy){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.TimeToCompleteBy = TimeToCompleteBy;
    }

    public void setTimeCreated(Calendar TimeCreated){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.TimeCreated = TimeCreated;
    }

    public void setTimeCompletedOn(Calendar TimeCompletedOn){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.TimeCompletedOn = TimeCompletedOn;
    }

    public void setUniversalID(String UniversalID){

        this.TimeLastEdited = Calendar.getInstance();

        this.UniversalID = UniversalID;
    }

    public void setCreatorID(String CreatorID){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.CreatorID = CreatorID;
    }

    public void setTitle(String Title){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.Title = Title;
    }

    public void setBody(String Body){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.Body = Body;
    }

    public void setIsReminder(boolean IsReminder){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.IsReminder = IsReminder;
    }

    public void setHasDate(boolean HasDate){

        //update last edited time
        this.TimeLastEdited = Calendar.getInstance();

        this.HasDate = HasDate;
    }

    public void setIsComplete(boolean IsComplete){

        //var
        Calendar Temp = Calendar.getInstance();


        //update completion time
        if(!this.IsComplete && IsComplete){

            this.TimeCompletedOn = Calendar.getInstance();

        }

        //update last edited time
        this.TimeLastEdited = Temp;
        this.TimeCompletedOn = Temp;
    }

    static Calendar getEmptyCalendar(){

        //obj
        Calendar Temp = Calendar.getInstance();

        //Assign
        Temp.set(0,0,0,0,0, 0);
        Temp.set(Calendar.DAY_OF_YEAR, 0);
        Temp.set(Calendar.DAY_OF_WEEK, 0);
        Temp.set(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
        Temp.set(Calendar.AM_PM, 0);
        Temp.set(Calendar.HOUR, 0);
        Temp.set(Calendar.MILLISECOND, 0);

        //This leaves only the locations, raw offsets, daylight savings, and zone offset correct

        return Temp;

    }
}

