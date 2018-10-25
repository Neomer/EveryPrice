using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{
    public class TagEditModel
    {

        public String Value;

        public void ToModel(ref ITag model)
        {
            model.Value = Value;
        }
    }
}