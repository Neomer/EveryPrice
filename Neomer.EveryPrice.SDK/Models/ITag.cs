using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface ITag : IEntity
    {
        String Value { get; set; }

        IList<IShop> Shops { get; set; }
    }
}
