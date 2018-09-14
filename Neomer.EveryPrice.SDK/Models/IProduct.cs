using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IProduct : IEntity
    {

        string Name { get; set; }

        IShop Shop { get; set; }

        IGroup Group { get; set; }

    }
}
