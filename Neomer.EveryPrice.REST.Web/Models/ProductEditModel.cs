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

        public List<PriceEditModel> Prices;

        public void ToProduct(ref IProduct model)
        {
            if (model == null)
            {
                return;
            }
            model.Name = Name;
            IUser user = model.Creator;
            IProduct product = model;

            var prices = Prices.Select(_ => new Price()
            {
                CreationDate = DateTime.UtcNow,
                Creator = user,
                Product = product,
                Value = _.Value,
                Unit = "шт."
            }).ToList<IPrice>();
            model.Prices = (ICollection<IPrice>)prices;
        }
    }
}