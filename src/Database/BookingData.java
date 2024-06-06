
package Database;

/**
 *
 * @author den51
 */
public class BookingData {
    
    protected String destination,date;
    protected Integer childId;
    public BookingData(String destination,String date){
        this.destination =destination;
        this.date=date;
    }
    public BookingData(String destination,String date, Integer children_id){
        this.destination =destination;
        this.date=date;
        this.childId = children_id;
    }
    public Integer getChildId(){
        return childId;
    }
    public String getDestination(){
    return destination;}
    
    public String getDate(){
    return date;}
    
    public void setDestination(String destination){
    this.destination=destination;
    }
    
    public void setDate(String date){
    this.date=date;}
    
}
