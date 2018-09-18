using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    [Serializable]
    public class ProductEditModel
    {
        public string Name;

        public void ToProduct(ref IProduct model)
        {
            if (model == null)
            {
                return;
            }
            model.Name = Name;
        }
    }
}