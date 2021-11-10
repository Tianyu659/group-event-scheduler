package csci310.models;

public class EventSearch {
    public enum Parameter {
        id,keyword,attractionId,venueId,postalCode,latLong,
        radius,unit,source,locale,marketId,startDateTime,
        endDateTime,includeTBA,includeTBD,includeTest,size,
        page,sort,onsaleStartDateTime,onsaleEndDateTime,city,
        countryCode,stateCode,classificationName,
        classificationId,dmaId,localStartDateTime,
        localStartEndDateTime,startEndDateTime,
        publicVisibilityStartDateTime,preSaleDateTime,
        onsaleOnStartDate,onsaleOnAfterStartDate,collectionId,
        segmentId,segmentName,includeFamily,promoterId,genreId,
        subGenreId,typeId,subTypeId,geoPoint,preferredCountry,
        includeSpellcheck,domain;
    
        @Override public String toString() {return name();}
    
    }
    static String sanitize(String in) {
        return "";
    }
    static String array(final String...strings) {
        return "";
    }
    
    final String[] data = new String[Parameter.values().length];
    public void put(final Parameter param,final String...values) {
    
    }
    
    @Override
    public String toString() {
        return "";
    }
}