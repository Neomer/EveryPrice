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

		public PriceEditModel Price;

        public void ToProduct(ref IProduct model)
        {
            if (model == null)
            {
                return;
            }
            model.Name = Name;
			if (model.Prices == null || !model.Prices.Any())
			{
				model.Prices = new List<IPrice>();
			}
			IPrice price = new Price()
			{
				CreationDate = DateTime.UtcNow,
				Creator = model.Creator
			};
			Price.ToPrice(ref price);
			((List<IPrice>)model.Prices).Add(price);
        }
    }
}