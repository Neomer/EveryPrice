﻿using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    [Serializable]
    public class ShopEditModel
    {
        public string Name;

        public double Lat;

        public double Lng;

        public string Address;

        public void ToShop(ref IShop shopModel)
        {
            if (shopModel == null)
            {
                return;
            }

            shopModel.Address = this.Address;
            shopModel.Lat = this.Lat;
            shopModel.Lng = this.Lng;
            shopModel.Name = this.Name;

        }
    }
}