using Neomer.EveryPrice.SDK.Exceptions.Managers;
using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Managers
{
    public class ShopManager : BaseEntityManager<ShopManager, IShop>
    {
        protected ShopManager()
        {

        }

        public override void Save(IEntity entity)
        {
            IShop shop = entity as IShop;

            if (shop == null)
            {
                throw new UnsupportedEntityException();
            }

            if (string.IsNullOrEmpty(shop.Name))
            {
                throw new FormatException("Название магазина не может быть пустым!");
            }

            base.Save(entity);
        }
    }
}
