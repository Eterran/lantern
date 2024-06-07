
package Database;

/**
 *
 * @author den51
 */
public class BookingData {
    
    protected String destination,date;

    public BookingData(String destination,String date){
        this.destination =destination;
        this.date=date;
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
