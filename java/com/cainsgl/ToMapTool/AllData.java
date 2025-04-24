package com.cainsgl.ToMapTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllData implements Serializable
{
  public   List<fishData> fishDataList;
   public Info info;
    public List<boomData> boomDataList;
    public AllData(List<fishData> fishDataList, Info info, List<boomData> boomDataList)
    {
        this.fishDataList =fishDataList;
        this.info = info;
        this.boomDataList=boomDataList;
    }
    public AllData()
    {

    }
}
