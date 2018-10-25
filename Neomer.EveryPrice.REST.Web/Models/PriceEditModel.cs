using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    [Serializable]
    public class PriceEditModel
    {
        public double Value;

        public string Unit;

        public void ToPrice(ref IPrice model)
        {
            if (model == null)
            {
                return;
            }
            model.Value = Value;
            model.Unit = Unit;
        }
    }
}