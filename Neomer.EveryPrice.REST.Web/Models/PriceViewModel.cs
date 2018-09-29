using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class PriceViewModel
    {
        public PriceViewModel()
        {

        }

        public PriceViewModel(IPrice price)
        {
            if (price == null)
            {
                return;
            }

            Uid = price.Uid;
            Value = price.Value;
            CreationDate = price.CreationDate;
            Unit = price.Unit;
        }

        public Guid Uid;

        public double Value;

        public DateTime CreationDate;

        public string Unit;
    }
}