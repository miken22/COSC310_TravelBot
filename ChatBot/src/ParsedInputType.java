public enum ParsedInputType {
    None,                   //
    TooLong,
    DontUnderstand,         //
    Greeting,               //
    Farewell,               //
    PleaseComeBack,         //
    Thanks,
    GetKeyword,
    SetDestination,         //
    CheckWeather,           //
    Distance,
    Activity,   
    SkiResort,
    Food,                   // Just added food case for more dialogue if possible.
    Accomodations,
    TravelMethod,           //
    GetAround,              //
    Language,               //
    ListCities,             //  For distances
    Dates,   
    Query,   
    Budget,                 //  amount affordable for hotels
    SimpleYes,              //
    SimpleNo,               //
    Debug_Reset,
    Debug_ShowStats,
    Debug_Enable, 
    BadDestination, 
    NotEnoughInfo, 
    GreetingUser, 
    FarewellUser, 
    Directions,
    Translate,
    Wiki;

    public boolean isWellFormed() {
        return (this != ParsedInputType.None) &&
                (this != ParsedInputType.DontUnderstand) &&
                (this != ParsedInputType.TooLong) &&
                (this != ParsedInputType.Debug_Reset) &&
                (this != ParsedInputType.Debug_Enable) &&
                (this != ParsedInputType.Debug_ShowStats);
    }
}